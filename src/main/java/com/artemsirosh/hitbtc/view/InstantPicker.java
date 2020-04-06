package com.artemsirosh.hitbtc.view;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;

import java.time.*;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created on 02 Dec, 2019.
 *
 * Pick date and time simultaneously. Work in UTC only because for Vaadin 14 no
 * chance to define user's timezone.
 *
 * @author Artemis A. Sirosh
 */
public class InstantPicker extends CustomField<Instant> {

    private final Supplier<Instant> modelValueReader;
    private final Consumer<Instant> modelValueSetter;
    private final ValueChangeListener<ComponentValueChangeEvent<CustomField<Instant>, Instant>> listener;


    /**
     * Creates an InstantPicker instance. {@code thresholdAdjuster} needs only to
     * set minimal or maximal value of field. If this not needed may passed no-op
     * lambda, like
     * <pre>(date, time) -> {}</pre>
     *
     * @param label of field
     * @param thresholdAdjuster adjust minimal or maximal value of field
     */
    public InstantPicker(String label, ThresholdAdjuster thresholdAdjuster) {
        final DatePicker datePicker = new DatePicker();
        final TimePicker timePicker = new TimePicker();
        final ZoneOffset utcOffset = ZoneOffset.UTC;
        final Function<Instant, OffsetDateTime> instantToDateTime = ts -> ts.atOffset(utcOffset);

        this.modelValueReader = () -> Optional.ofNullable(datePicker.getValue())
            .filter(dp -> Objects.nonNull(timePicker.getValue()))
            .map(date -> LocalDateTime.of(date, timePicker.getValue()).toInstant(utcOffset))
            .orElse(null);

        this.modelValueSetter = value -> Optional.ofNullable(value)
            .map(instantToDateTime)
            .ifPresent(dateTime -> {
                datePicker.setValue(dateTime.toLocalDate());
                timePicker.setValue(dateTime.toLocalTime());
            });

        this.listener = event -> Optional.ofNullable(event.getValue()).ifPresent(instant -> {
            final OffsetDateTime dt = instantToDateTime.apply(instant);
            thresholdAdjuster.adjust(dt.toLocalDate(), dt.toLocalTime(), datePicker, timePicker);
        });

        this.setLabel(label);
        this.add(datePicker, timePicker);
    }

    /**
     * Returns listener which able to set up minimal or maximal allowed value.
     * @return a {@link com.vaadin.flow.component.HasValue.ValueChangeListener} instance.
     */
    public ValueChangeListener<ComponentValueChangeEvent<CustomField<Instant>, Instant>> getListener() {
        return listener;
    }

    @Override
    protected Instant generateModelValue() {
        return modelValueReader.get();
    }

    @Override
    protected void setPresentationValue(Instant newPresentationValue) {
        modelValueSetter.accept(newPresentationValue);
    }

    @FunctionalInterface
    public interface ThresholdAdjuster {

        void adjust(LocalDate date, LocalTime time, DatePicker datePicker, TimePicker timePicker);
    }
}

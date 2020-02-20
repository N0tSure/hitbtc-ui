package com.artemsirosh.hitbtc.model;

import java.util.function.Function;

/**
 * Created on 24 Nov, 2019.
 *
 * Sorting direction.
 *
 * @author Artemis A. Sirosh
 */
public enum SortDirection {

    ASC, DESC;

    public static Function<com.vaadin.flow.data.provider.SortDirection, SortDirection> modelConverter =
        direction -> SortDirection.values()[direction.ordinal()];

}

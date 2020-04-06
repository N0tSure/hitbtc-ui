# HitBTC UI

This is a simple demo for GUI application created using Vaadin. **HitBTC API** used as data provider. There is view for
[symbols](https://api.hitbtc.com/#symbols) and for [candles](https://api.hitbtc.com/#candles).

## Symbols

Symbols view allows:

 * view available symbols
 * sort them by a lot of fields
 * find symbols by identifiers
 * view symbol's details _SymbolDetails_ pane

## Candles

From **SymbolDetails** user can go to the **CandlesView**. There user can:

 * view candles for particular symbols
 * query candles by Candle's period
 * query candles by timestamp lower and upper bound
 * sort candles by timestamp

## Technology stack

 * Vaadin
 * Spring Framework
 * Feign
 * AssertJ
 * Gradle

package org.example.Amazon.Cost;

import org.example.Amazon.Item;

import java.util.List;

public class DeliveryPrice implements PriceRule {
    @Override
    public double priceToAggregate(List<Item> cart) {

        int totalItems = cart.size();

        if(totalItems == 0)
            return 0;
        if(totalItems >= 1 && totalItems <= 3)
            // This condition 'totalItems >= 1' is always true because the previous check 'totalItems == 0'
            // ensures that totalItems is never less than 1. This means that the false branch is never executed for values < 1, which prevents full branch coverage.
            return 5;
        if(totalItems >= 4 && totalItems <= 10) // Same reasoning as above since the condition always evaluate to true.
            return 12.5;

        return 20.0;
    }
}

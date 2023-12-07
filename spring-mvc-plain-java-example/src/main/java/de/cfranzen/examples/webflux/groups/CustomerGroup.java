package de.cfranzen.examples.webflux.groups;

import java.util.List;

public record CustomerGroup(
        String name,
        List<CustomerGroupEntry> entries
) {
}

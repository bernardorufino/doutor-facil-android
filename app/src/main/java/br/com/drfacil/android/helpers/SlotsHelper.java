package br.com.drfacil.android.helpers;

import br.com.drfacil.android.model.Slot;
import org.joda.time.LocalDate;

import java.util.*;

public class SlotsHelper {

    public static Map<LocalDate, List<Slot>> distribute(Collection<? extends Slot> slots) {
        Map<LocalDate, List<Slot>> map = new TreeMap<>();
        List<Slot> ordered = new ArrayList<>(slots);
        Collections.sort(ordered);
        for (Slot slot : ordered) {
            LocalDate day = new LocalDate(slot.getStartDate());
            List<Slot> list = map.get(day);
            if (list == null) {
                list = new ArrayList<>();
                map.put(day, list);
            }
            list.add(slot);
        }
        return map;
    }

    // Prevents instantiation
    private SlotsHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}

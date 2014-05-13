package br.com.drfacil.android.helpers;

import br.com.drfacil.android.model.Model;

import java.util.Collection;

public class ModelsHelper {

    public static <T extends Model> String getModelIdsAsCsv(Collection<T> models) {
        StringBuilder ans = new StringBuilder();
        for (T model : models) {
            ans.append(model.getId()).append(",");
        }
        return ans.substring(0, Math.max(ans.length() - 1, 0));
    }

    // Prevents instantiation
    private ModelsHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}

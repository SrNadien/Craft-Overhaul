package nadiendev.craftoverhaul.datagen;

import nadiendev.craftoverhaul.providers.ModAdvancementsProvider;
import nadiendev.craftoverhaul.providers.ModRecipeProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class CraftOverhaulDatagen {
    private CraftOverhaulDatagen() {}

    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider((output, registries) -> new ModAdvancementsProvider(output, registries));
    }
}
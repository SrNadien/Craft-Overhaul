package nadiendev.craftoverhaul.datagen;

import nadiendev.craftoverhaul.providers.ModAdvancementsProvider;
import nadiendev.craftoverhaul.providers.ModRecipeProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class CraftOverhaulDatagen {
    private CraftOverhaulDatagen() {}

    public static void gatherData(GatherDataEvent.Client event) {
        event.createReloadableRegistryObjects(new RegistrySetBuilder()
                .add(Registries.ADVANCEMENT, ModAdvancementsProvider.create())
                .add(ModRecipeProvider.create()));
    }
}
package cy.jdkdigital.dyenamicsandfriends.common.block.entity.supplementaries;

import cofh.dyenamics.core.util.DyenamicDyeColor;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import cy.jdkdigital.dyenamicsandfriends.common.block.supplementaries.DyenamicsFlagBlock;
import net.mehvahdjukaar.supplementaries.common.block.tiles.FlagBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class DyenamicsFlagBlockEntity extends FlagBlockTile
{
    private final DyenamicsFlagBlock block;
    private List<Pair<BannerPattern, DyenamicDyeColor>> patterns;

    public DyenamicsFlagBlockEntity(DyenamicsFlagBlock block, BlockPos pos, BlockState state) {
        super(pos, state);
        this.block = block;
    }

    @Override
    public BlockEntityType<?> getType() {
        return block.getBlockEntitySupplier().get();
    }

    public List<Pair<BannerPattern, DyenamicDyeColor>> getDyenamicsPatterns() {
        if (this.patterns == null) {
            var dyeColorPatterns = super.getPatterns();
            dyeColorPatterns.removeIf(pattern -> pattern.getFirst().equals(BannerPattern.BASE));
            var itemPatterns = dyeColorPatterns.stream().map(Pair::getFirst).distinct().toList();
            this.patterns = createPatterns(block.getDyenamicsColor(), itemPatterns);
        }
        return this.patterns;
    }

    public static List<Pair<BannerPattern, DyenamicDyeColor>> createPatterns(DyenamicDyeColor pColor, ListTag patterns) {
        List<BannerPattern> list = Lists.newArrayList();
        if (patterns != null) {
            for(int i = 0; i < patterns.size(); ++i) {
                CompoundTag compoundtag = patterns.getCompound(i);
                BannerPattern bannerPattern = BannerPattern.byHash(compoundtag.getString("Pattern"));
                if (bannerPattern != null) {
                    list.add(bannerPattern);
                }
            }
        }
        return createPatterns(pColor, list);
    }

    public static List<Pair<BannerPattern, DyenamicDyeColor>> createPatterns(DyenamicDyeColor pColor, List<BannerPattern> patterns) {
        List<Pair<BannerPattern, DyenamicDyeColor>> list = Lists.newArrayList();
        list.add(Pair.of(BannerPattern.BASE, pColor));
        if (patterns.size() > 0) {
            for(BannerPattern bannerPattern: patterns) {
                if (bannerPattern != null) {
                    list.add(Pair.of(bannerPattern, pColor));
                }
            }
        }
        return list;
    }
}

import shutil

templates = [
  r'templates/assets/dyenamicsandfriends/blockstates/create_$$_valve_handle.json',
  r'templates/assets/dyenamicsandfriends/blockstates/create_$$_seat.json',
  r'templates/assets/dyenamicsandfriends/blockstates/create_$$_sail.json',
  r'templates/assets/dyenamicsandfriends/blockstates/another_furniture_$$_curtain.json',
  r'templates/assets/dyenamicsandfriends/blockstates/another_furniture_$$_lamp.json',
  r'templates/assets/dyenamicsandfriends/blockstates/another_furniture_$$_sofa.json',
  r'templates/assets/dyenamicsandfriends/blockstates/another_furniture_$$_stool.json',
  r'templates/assets/dyenamicsandfriends/blockstates/another_furniture_$$_tall_stool.json',
  r'templates/assets/dyenamicsandfriends/blockstates/comforts_$$_hammock.json',
  r'templates/assets/dyenamicsandfriends/blockstates/comforts_$$_sleeping_bag.json',
  r'templates/assets/dyenamicsandfriends/blockstates/elevatorid_$$_elevator.json',
  r'templates/assets/dyenamicsandfriends/blockstates/quark_$$_framed_glass.json',
  r'templates/assets/dyenamicsandfriends/blockstates/quark_$$_framed_glass_pane.json',
  r'templates/assets/dyenamicsandfriends/blockstates/quark_$$_shingles.json',
  r'templates/assets/dyenamicsandfriends/blockstates/quark_$$_shingles_slab.json',
  r'templates/assets/dyenamicsandfriends/blockstates/quark_$$_shingles_stairs.json',

  r'templates/assets/dyenamicsandfriends/models/block/create_$$_valve_handle.json',
  r'templates/assets/dyenamicsandfriends/models/block/create_$$_seat.json',
  r'templates/assets/dyenamicsandfriends/models/block/create_$$_sail.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_lamp.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_lamp_off.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_lamp_tall_top.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_lamp_tall_top_off.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_lamp_wall.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_lamp_wall_off.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_sofa.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_sofa_inner.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_sofa_left.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_sofa_middle.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_sofa_outer.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_sofa_right.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_stool.json',
  r'templates/assets/dyenamicsandfriends/models/block/another_furniture_$$_tall_stool.json',
  r'templates/assets/dyenamicsandfriends/models/block/comforts_$$_hammock.json',
  r'templates/assets/dyenamicsandfriends/models/block/comforts_$$_sleeping_bag.json',
  r'templates/assets/dyenamicsandfriends/models/block/elevatorid_$$_elevator.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_framed_glass.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_framed_glass_pane_noside.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_framed_glass_pane_noside_alt.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_framed_glass_pane_post.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_framed_glass_pane_side.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_framed_glass_pane_side_alt.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_shingles.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_shingles_slab.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_shingles_slab_top.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_shingles_stairs.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_shingles_stairs_inner.json',
  r'templates/assets/dyenamicsandfriends/models/block/quark_$$_shingles_stairs_outer.json',

  r'templates/assets/dyenamicsandfriends/models/item/create_$$_valve_handle.json',
  r'templates/assets/dyenamicsandfriends/models/item/create_$$_seat.json',
  r'templates/assets/dyenamicsandfriends/models/item/another_furniture_$$_curtain.json',
  r'templates/assets/dyenamicsandfriends/models/item/another_furniture_$$_lamp.json',
  r'templates/assets/dyenamicsandfriends/models/item/another_furniture_$$_sofa.json',
  r'templates/assets/dyenamicsandfriends/models/item/another_furniture_$$_stool.json',
  r'templates/assets/dyenamicsandfriends/models/item/another_furniture_$$_tall_stool.json',
  r'templates/assets/dyenamicsandfriends/models/item/comforts_$$_hammock.json',
  r'templates/assets/dyenamicsandfriends/models/item/comforts_$$_sleeping_bag.json',
  r'templates/assets/dyenamicsandfriends/models/item/elevatorid_$$_elevator.json',
  r'templates/assets/dyenamicsandfriends/models/item/quark_$$_shard.json',
  r'templates/assets/dyenamicsandfriends/models/item/quark_$$_rune.json',
  r'templates/assets/dyenamicsandfriends/models/item/quark_$$_framed_glass.json',
  r'templates/assets/dyenamicsandfriends/models/item/quark_$$_framed_glass_pane.json',
  r'templates/assets/dyenamicsandfriends/models/item/quark_$$_shingles.json',
  r'templates/assets/dyenamicsandfriends/models/item/quark_$$_shingles_slab.json',
  r'templates/assets/dyenamicsandfriends/models/item/quark_$$_shingles_stairs.json',

  r'templates/data/dyenamicsandfriends/recipes/create/$$_seat.json',
  r'templates/data/dyenamicsandfriends/recipes/create/$$_seat_from_other_seat.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_curtain.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_curtain_dyeing.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_lamp.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_sofa.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_stool.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_stool_dyeing.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_tall_stool.json',
  r'templates/data/dyenamicsandfriends/recipes/another_furniture/$$_tall_stool_dyeing.json',
  r'templates/data/dyenamicsandfriends/recipes/comforts/$$_hammock.json',
  r'templates/data/dyenamicsandfriends/recipes/comforts/$$_sleeping_bag.json',
  r'templates/data/dyenamicsandfriends/recipes/elevatorid/$$_elevator.json',
  r'templates/data/dyenamicsandfriends/recipes/elevatorid/redye_$$_elevator.json',
  r'templates/data/dyenamicsandfriends/recipes/quark/$$_glass.json',
  r'templates/data/dyenamicsandfriends/recipes/quark/$$_framed_glass.json',
  r'templates/data/dyenamicsandfriends/recipes/quark/$$_framed_glass_pane.json',
  r'templates/data/dyenamicsandfriends/recipes/quark/$$_shingles.json',
  r'templates/data/dyenamicsandfriends/recipes/quark/$$_shingles_dye.json',
  r'templates/data/dyenamicsandfriends/recipes/quark/$$_shingles_slab.json',
  r'templates/data/dyenamicsandfriends/recipes/quark/$$_shingles_stairs.json',

  r'templates/data/dyenamicsandfriends/loot_tables/blocks/create_$$_seat.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/create_$$_valve_handle.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/another_furniture_$$_curtain.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/another_furniture_$$_lamp.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/another_furniture_$$_sofa.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/another_furniture_$$_stool.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/another_furniture_$$_tall_stool.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/comforts_$$_hammock.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/comforts_$$_sleeping_bag.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/elevatorid_$$_elevator.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/quark_$$_framed_glass.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/quark_$$_framed_glass_pane.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/quark_$$_shingles.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/quark_$$_shingles_slab.json',
  r'templates/data/dyenamicsandfriends/loot_tables/blocks/quark_$$_shingles_stairs.json',

  r'templates/data/dyenamicsandfriends/loot_modifiers/quark_$$_shard.json',
]

colors = ["peach","aquamarine","fluorescent","mint","maroon","bubblegum","lavender","persimmon","cherenkov"]

for t in range(len(templates)):
  filename = templates[t]
  for x in range(len(colors)):
    resFile = filename.replace("$$", colors[x]).replace("templates/", "src/main/resources/")
    shutil.copyfile(filename, resFile)
    with open(resFile, "r") as f:
      read_data = f.read()
      line = read_data.replace('$$', colors[x])
      f.close()

    with open(resFile, "w") as f:
      f.write(line)
      f.close()
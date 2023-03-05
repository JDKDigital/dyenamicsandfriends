import shutil
import os

colors = ["peach","aquamarine","fluorescent","mint","maroon","bubblegum","lavender","persimmon","cherenkov"]

for path, subdirs, files in os.walk('templates'):
  for name in files:
    filename = os.path.join(path, name)
    if "bumblezone" in filename:
      continue
    if "handcrafted" in filename:
      continue
    if "glazedresymmetry" in filename:
      continue
    for x in range(len(colors)):
      resFile = filename.replace("$$", colors[x]).replace("templates\\", "src\\main\\resources\\")
#       print(resFile)
      shutil.copyfile(filename, resFile)
      with open(resFile, "r") as f:
        read_data = f.read()
        line = read_data.replace('$$', colors[x])
        f.close()
      with open(resFile, "w") as f:
        f.write(line)
        f.close()
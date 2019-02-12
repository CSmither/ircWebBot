#!env python
import os
import sys

templatable = ["jobs", "commands"]
config = ""
with open("config/configTemplate.yml", "r") as template:
    config = template.read()
    for section in templatable:
        sections = ""
        for file in sorted(os.listdir("config/" + section)):
            with open("config/" + section + "/" + file, "r") as sectionFile:
                sections += os.path.splitext(os.path.basename(file))[0] + ":\n  " + sectionFile.read().replace("\n",
                                                                                                               "\n  ") + "\n"
        config = config.replace("{{" + section + "}}", "\n  " + sections.replace("\n", "\n  "))

oldConfig = ""
with open("config.yml", "r") as configFile:
    oldConfig = configFile.read()
if (oldConfig != config):
    print("Config file old, rewriting...")
    with open("config.yml", "w") as configFile:
        configFile.write(config)
    sys.exit(1)
else:
    sys.exit(0)

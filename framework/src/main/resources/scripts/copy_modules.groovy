// Copy each module in resources directory

import org.apache.commons.io.FileUtils

def source = new File("src/main/java/fr/unice/polytech/si5/pfe46/modules");
def target = new File("src/main/resources/modules")
FileUtils.copyDirectory(source, target)

source = new File("src/main/java/fr/unice/polytech/si5/pfe46/utils/JsonProcess.java")
target = new File("src/main/resources/modules/JsonProcess.java")
FileUtils.copyFile(source, target)

FileUtils.forceDelete(new File("src/main/resources/modules/Main.java"))
package fr.unice.polytech.si5.pfe46.engine.inputtype.methods;

public class LibraryMethodBinding extends MethodBinding {

    private String methodCode;
    private String[] imports;

    public LibraryMethodBinding()
    {
        imports = new String[100];
    }

    public String getMethodCode() { return methodCode; }
    public void setMethodCode(String code) { this.methodCode = code; }

    public String[] getImports() { return imports; }
    public void setImports(String[] imports) { this.imports = imports; }
}

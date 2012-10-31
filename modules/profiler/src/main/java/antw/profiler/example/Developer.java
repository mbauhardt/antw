package antw.profiler.example;

public class Developer {

    private String _name;
    private Language _language;

    public Developer(String name, String language) {
        _name = name;
        _language = new Language(language);
        _language.getName();
    }

    public Developer(String name, Language language) {
        _name = name;
        _language = language;
    }

    public String getName() {
        return _name;
    }

    public Language getLanguage() {
        return _language;
    }

}

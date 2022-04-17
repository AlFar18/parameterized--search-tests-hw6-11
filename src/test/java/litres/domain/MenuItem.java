package litres.domain;

public enum MenuItem {
    ALLRESULTS ("Все результаты"), EBOOKS ("Электронные книги"), ABOOKS ("Аудиокниги");
    public final String rusName;

    MenuItem(String rusName) {
        this.rusName = rusName;
    }
}

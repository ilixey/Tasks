package com.vaspiakou;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {

        Library library = new Library();

        // 1. Сформировать список рассылки из почтовых адресов, учитывая является ли читатель подписчиком
                                                                                                                                                                                // library.readers.stream().filter(reader -> reader.isSubscriber() == true).map(reader -> reader.getEmail()).toList();

        // 2. Получить список всех книг, взятых читателями. Список не должен содержать дубликатов (книг одного автора, с одинаковым названием и годом издания).
                                                                                                                                                                                        //library.readers.stream().flatMap(reader -> reader.getBooks().stream()).distinct().map(book -> book.name).forEach(System.out::println);

        // 3. Проверить, взял ли кто-то из читателей библиотеки какие-нибудь книги Оруэлла.
                                                                                                                                                                                //System.out.println(library.readers.stream().flatMap(reader -> reader.getBooks().stream()).map(Book::getAuthor).anyMatch(author -> author.equals("Чехов")));

        // 4. Узнать наибольшее число книг, которое сейчас на руках у читателя.
//        int a = library.readers.stream().mapToInt(reader -> reader.getBooks().size()).max().getAsInt();
//        System.out.println(a);
                                                                                                                                                                                    //System.out.println(library.readers.stream().mapToInt(reader -> (int)reader.getBooks().stream().count()).max().orElse(-1));

        //Нужно не просто отправить письма всем, кто согласился на рассылку, — будем рассылать разные тексты двум группам:
        //тем, у кого взято меньше двух книг, просто расскажем о новинках библиотеки;
        //тем, у кого две книги и больше, напомним о том, что их нужно вернуть в срок
        //

        //вывести список тех читателей, у которых взяты одинаковые книги.

        library.getReaders().stream().

                                                                                                                                                                                    //library.readers.stream().filter(reader -> reader.isSubscriber()).collect(Collectors.groupingBy(reader -> reader.getBooks().size() >= 2));

    }
    public static class Book {
        private String author; //Автор
        private String name;	//Название
        private Integer issueYear; //Год издания

        public Book(String author, String name, Integer issueYear)   {
            this.author = author;
            this.name = name;
            this.issueYear = issueYear;
        }

        public String getAuthor() {
            return author;
        }

        public String getName() {
            return name;
        }

        public Integer getIssueYear() {
            return issueYear;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "author='" + author + '\'' +
                    ", name='" + name + '\'' +
                    ", issueYear=" + issueYear +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Book book = (Book) o;
            return author.equals(book.author) &&
                    name.equals(book.name) &&
                    issueYear.equals(book.issueYear);
        }

        @Override
        public int hashCode() {
            return Objects.hash(author, name, issueYear);
        }
    }

    public static class Reader {
        private String fio; //ФИО
        private String email; //электронный адрес
        private boolean subscriber; //флаг согласия на рассылку
        private List<Book> books; //взятые книги

        public Reader(String fio, String email, boolean subscriber) {
            this.fio = fio;
            this.email = email;
            this.subscriber = subscriber;
            this.books = new ArrayList<>();
        }

        public boolean isSubscriber() {
            return subscriber;
        }

        public String getFio() {
            return fio;
        }

        public String getEmail() {
            return email;
        }

        public List<Book> getBooks() {
            return books;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Reader reader = (Reader) o;
            return fio.equals(reader.fio);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fio);
        }
    }

    public static class EmailAddress {
        private String email; //электронный адрес
        private String someData; /*доп. информация для формирования письма. В примерах не используем — добавили, чтобы оправдать существование отдельного класса :)*/

        public EmailAddress(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public String getSomeData() {
            return someData;
        }

        public void setSomeData(String someData) {
            this.someData = someData;
        }
    }

    public static class Library {

        private List<Book> books;
        private List<Reader> readers;

        public Library() {
            init();
        }

        private void init() {
            books = new ArrayList<>();
            books.add(new Book("Оруэлл", "1984", 2021));
            books.add(new Book("Булгаков", "Мастер и Маргарита", 2019));
            books.add(new Book("Пушкин", "Сборник стихотворений", 2014));
            books.add(new Book("Толстой", "Война и мир", 2022));
            books.add(new Book("Чехов", "Вишневый сад", 2005));
            //и так далее для других книг

            readers = new ArrayList<>();
            readers.add(new Reader("Иванов Иван Иванович", "ivanov.email@test.ru", true));
            readers.add(new Reader("Михайлов Михаил Михалыч", "mixa.email@test.ru", false));
            readers.add(new Reader("Андреев Андрей Андреич", "andruxa.email@test.ru", true));
            //и так далее для других читателей

            readers.get(0).getBooks().add(books.get(1));
            readers.get(1).getBooks().add(books.get(2));
            readers.get(2).getBooks().add(books.get(1));
            readers.get(2).getBooks().add(books.get(2));
            readers.get(2).getBooks().add(books.get(1));
            //и так далее для других читателей и взятых книг
        }

        public List<Book> getBooks() {
            return books;
        }

        public List<Reader> getReaders() {
            return readers;
        }
    }
}

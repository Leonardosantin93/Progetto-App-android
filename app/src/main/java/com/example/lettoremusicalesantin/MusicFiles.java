package com.example.lettoremusicalesantin;

public class MusicFiles {
    private String path;
    private String titolo;
    private String artista;
    private String album;
    private String durata;

    public MusicFiles(String path, String titolo, String artista, String album, String durata) {
        this.path = path;
        this.titolo = titolo;
        this.artista = artista;
        this.album = album;
        this.durata = durata;
    }

    public MusicFiles() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }
}

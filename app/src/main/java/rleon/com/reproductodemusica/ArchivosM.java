package rleon.com.reproductodemusica;

public class ArchivosM {
    private String path;
    private String cancion;
    private String artista;
    private String album;
    private String tiempo;
    private String id;

    public ArchivosM( String cancion, String artista, String album, String tiempo, String path, String id) {
        this.path = path;
        this.cancion = cancion;
        this.artista = artista;
        this.album = album;
        this.tiempo = tiempo;
        this.id = id;
    }

    public ArchivosM() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

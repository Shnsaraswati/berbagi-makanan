package com.shnsaraswati.applikasiberbagimakanan.model;

import java.util.Comparator;

public class Model {
    private String namaAkun,namaMakanan,lokasi;
    private int img,dilihat;


    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getDilihat() {
        return dilihat;
    }

    public void setDilihat(int dilihat) { this.dilihat = dilihat;
    }

    public static final Comparator<Model>By_TITLE_ASCENDING = new Comparator<Model>() {
        @Override
        public int compare(Model o1, Model o2) {
            return o1.getNamaAkun().compareTo(o2.getNamaAkun());
        }
    };

    public static final Comparator<Model>By_TITLE_DESCENDING= new Comparator<Model>() {
        @Override
        public int compare(Model o1, Model o2) {
            return o2.getNamaAkun().compareTo(o1.getNamaAkun());
        }
    };
}

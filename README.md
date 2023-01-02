# **Final Project Kelompok 5**

### Oleh:
- Septi Hidayah
- Suci Fitria Liana Aripin
- Taufik Fathurahman

---

## # Req Tugas Akhir
Menambahkan 1 fitur baru kedalam program Transportation Reservation System yang sudah ada.

## # Solusi
Fitur baru yang dibuat adalah voucher bagi user. Kami membuat tabel baru (```voucher```) yang memiliki relasi *Many to One* terhadap tabel ```user``` yang telah ada. Skema tabel baru adalah sebagai berikut:

![new_table_voucher](https://user-images.githubusercontent.com/38347258/210246200-48937c27-687b-4936-97c0-2721b66236f5.png)

### ## Operasi yang dapat dilakukan :
* ```getAll()``` => Menampilkan semua voucher yang ada
* ```addVoucher()``` => Menambahkan voucher baru
* ```getVoucherByOwner()``` => Menampilkan semua voucher yang dimiliki oleh user tertentu
* ```deleteVoucher()``` => Menghapus voucher
* ```getVoucherById()``` => Menampilkan voucher berdasarkan voucher id
* ```updateVoucher()``` => Memperbaharui voucher

## # Hasil Run Program
Testing menggunakan swagger 2, dan dapat diakses melalui link : [http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/)

Berikut ini hasil testing terhadap setiap use cash fitur voucher:

### ## getAll()
![getAllResult](https://user-images.githubusercontent.com/38347258/210255071-04e889d3-9a5d-413b-a02b-3ed228577859.png)

### ## addVoucher()
![addVoucherResult](https://user-images.githubusercontent.com/38347258/210255060-0f48eba2-c882-4ea2-9d48-f646730ee1a7.png)

### ## getVoucherByowner()
![getVoucherByOwner](https://user-images.githubusercontent.com/38347258/210255078-3b5b68a1-afd7-4d9b-ad72-81da993a36b1.png)

### ## deleteVoucher()
![deleteVoucher](https://user-images.githubusercontent.com/38347258/210255068-d07a1461-0f0b-41d8-99fc-2de2e4107bbb.png)

### ## getVoucherById()
![getVoucherById](https://user-images.githubusercontent.com/38347258/210255074-aad4c94c-4564-4441-aaff-679f6208040f.png)

### ## updateVoucher()
![updateVoucher](https://user-images.githubusercontent.com/38347258/210255082-02814eb1-aee0-4142-a237-d5cbed6e8afc.png)
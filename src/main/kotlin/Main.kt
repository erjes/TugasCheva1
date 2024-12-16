
enum class JenisGitar {
    AKUSTIK,
    ELEKTRIK,
    BAS,
    CLASSICAL
}

open class Akun(val username: String, val password: String)

class AkunPembeli(username: String, password: String) : Akun(username, password) {
    var saldo: Double = 0.0

    fun tambahSaldo(jumlah: Double) {
        saldo += jumlah
        println("Saldo berhasil ditambahkan! Saldo saat ini: $saldo")
    }

    fun beliGitar(gitar: Gitar, uangTunai: Double) {
        if (uangTunai >= gitar.harga) {
            saldo -= gitar.harga
            println("Pembelian berhasil! Anda telah membeli ${gitar.getNama()} seharga ${gitar.harga}.")
            println("Sisa saldo: $saldo")
        } else {
            println("Uang tunai tidak cukup untuk membeli ${gitar.getNama()}.")
        }
    }
}

class Gitar(private var nama: String, var harga: Double, private var jenis: JenisGitar) {
    fun getNama(): String = nama

    fun detailGitar() {
        println("""
            |Nama Gitar: $nama
            |Harga: $harga
            |Jenis: $jenis
        """.trimMargin())
    }
}

fun main() {
    val daftarGitar = mutableListOf<Gitar>()
    val daftarAkunPembeli = mutableListOf<AkunPembeli>()
    var pilihan: Int
    var role: String? = null
    var akunLogin: AkunPembeli? = null

    println("Selamat Datang di Toko Gitar")

    do {
        println("\n1. Registrasi Pembeli\n2. Login\n3. Keluar")
        print("Pilih opsi: ")
        pilihan = readLine()!!.toInt()

        when (pilihan) {
            1 -> {
                print("Username: ")
                val username = readLine()!!
                print("Password: ")
                val password = readLine()!!
                daftarAkunPembeli.add(AkunPembeli(username, password))
                println("Akun pembeli berhasil dibuat!")
            }
            2 -> {
                print("Username: ")
                val username = readLine()!!
                print("Password: ")
                val password = readLine()!!

                if (username == "admin" && password == "admin123") {
                    role = "admin"
                    println("Anda berhasil login sebagai Admin!")
                } else {
                    akunLogin = daftarAkunPembeli.find { it.username == username && it.password == password }
                    if (akunLogin != null) {
                        role = "pembeli"
                        println("Anda berhasil login sebagai Pembeli!")
                    } else {
                        println("Username atau password salah!")
                    }
                }
            }
            3 -> println("Keluar dari sistem!")
            else -> println("Pilihan tidak valid, silakan coba lagi.")
        }
    } while (pilihan != 3 && role == null)

    while (true) {
        when (role) {
            "admin" -> {
                do {
                    println("\nMenu Admin:\n1. Tambah Gitar\n2. Tampilkan Semua Gitar\n3. Keluar")
                    print("Pilih opsi: ")
                    pilihan = readLine()!!.toInt()

                    when (pilihan) {
                        1 -> {
                            print("Nama Gitar: ")
                            val nama = readLine()!!
                            print("Harga: ")
                            val harga = readLine()!!.toDouble()
                            print("Jenis (AKUSTIK, ELEKTRIK, BAS, CLASSICAL): ")
                            val jenis = JenisGitar.valueOf(readLine()!!.toUpperCase())
                            daftarGitar.add(Gitar(nama, harga, jenis))
                            println("Gitar berhasil ditambahkan!")
                        }
                        2 -> {
                            println("Daftar Semua Gitar:")
                            if (daftarGitar.isEmpty()) {
                                println("Tidak ada gitar yang tersedia.")
                            } else {
                                daftarGitar.forEach { it.detailGitar() }
                            }
                        }
                        3 -> {
                            println("Keluar dari menu admin!")
                            role = null
                        }
                        else -> println("Pilihan tidak valid, silakan coba lagi.")
                    }
                } while (pilihan != 3)
            }
            "pembeli" -> {
                do {
                    println("\nMenu Pembeli:\n1. Tampilkan Semua Gitar\n2. Tambah Saldo\n3. Beli Gitar\n4. Keluar")
                    print("Pilih opsi: ")
                    pilihan = readLine()!!.toInt()

                    when (pilihan) {
                        1 -> {
                            println("Daftar Semua Gitar:")
                            if (daftarGitar.isEmpty()) {
                                println("Tidak ada gitar yang tersedia.")
                            } else {
                                daftarGitar.forEach { it.detailGitar() }
                            }
                        }
                        2 -> {
                            print("Masukkan jumlah saldo yang ingin ditambahkan: ")
                            val jumlah = readLine()!!.toDouble()
                            akunLogin?.tambahSaldo(jumlah)
                        }
                        3 -> {
                            print("Masukkan nama gitar yang ingin dibeli: ")
                            val namaGitar = readLine()!!
                            val gitarDitemukan = daftarGitar.find { it.getNama() == namaGitar }

                            if (gitarDitemukan != null) {
                                print("Masukkan uang tunai: ")
                                val uangTunai = readLine()!!.toDouble()
                                akunLogin?.beliGitar(gitarDitemukan, uangTunai)
                            } else {
                                println("Gitar tidak ditemukan!")
                            }
                        }
                        4 -> {
                            println("Keluar dari menu pembeli!")
                            role = null
                        }
                        else -> println("Pilihan tidak valid, silakan coba lagi.")
                    }
                } while (pilihan != 4)
            }
            null -> break
        }
    }
}


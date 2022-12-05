/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.restful;

import java.util.HashMap;
import java.util.Map;
import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fuadi
 */
@RestController //mendefinisikan layanan web RESTful
public class ProductServiceController {
    private static Map<String,Product> productRepo = new HashMap<>(); //HashMap untuk menampilkan data
    static {
        Product honey = new Product(); //mendeklarasikan produk honey
        honey.setId("1"); //menambahkan ID produk
        honey.setName("Honey"); //menambahkan nama Produk
        productRepo.put(honey.getId(), honey);
        
        Product almond = new Product(); //mendeklarasikan produk almond
        almond.setId("2"); //menambahkan ID produk
        almond.setName("Almond"); //menambahkan nama produk
        productRepo.put(almond.getId(), almond);
    }
    //Method DELETE
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE) //menentukan URL untuk mengakses permintaan DELETE
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        productRepo.remove(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK); //menampilkan pesan ketika sukses menghapus data
    }
    //Method PUT
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT) //menentukan URL untuk mengakses permintaan PUT
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
        //if else statement(jika kondisi pertama tidak terpenuhi, maka kode program akan lanjut ke kondisi setelahnya)
        if (!productRepo.containsKey(id)){ //kondisi jika ID tidak ada
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND); //menampilkan pesan ketika ID yang akan diubah pada PUT tidak ada di data
        }else{ //kondisi jika ID ada
        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id, product);
        return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK); //menampilkan pesan ketika sukses mengubah nama produk pada ID yang diminta
        }
    }
    //Method POST
    @RequestMapping(value = "/products", method = RequestMethod.POST) //menentukan URL untuk mengakses permintaan POST
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        //if else statement(jika kondisi pertama tidak terpenuhi, maka kode program akan lanjut ke kondisi setelahnya)
        if (productRepo.containsKey(product.getId())){ //kondisi jika ID sudah ada
            return new ResponseEntity<>("Please enter another ID, because your entered ID already exists", HttpStatus.NOT_FOUND); //menampilkan pesan untuk memasukkan ID yang lain ketika kita memasukkan data yang IDnya sudah ada
        } else { //kondisi jika ID belum ada
        productRepo.put(product.getId(), product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED); //menampilkan pesan "Produk sukses ditambahkan" ketika sukses menambahkan data produk
        }
    }
    //Method GET
    @RequestMapping(value = "/products") //menentukan URL untuk mengakses permintaan GET
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
    }
}

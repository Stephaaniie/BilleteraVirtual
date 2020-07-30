package ar.com.ada.api.billeteravirtual;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.billeteravirtual.security.Crypto;
import ar.com.ada.api.billeteravirtual.services.implementations.BilleteraService;
import ar.com.ada.api.billeteravirtual.services.implementations.UsuarioService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	BilleteraService billeteraService;
	
	@Test
	void EncryptionTest() {
		String textoClaro = "Este es un texto que todos pueden leer";

		String unSaltoLoco = "un numero random";

		String textoEncriptado = "";

		textoEncriptado = Crypto.encrypt(textoClaro, unSaltoLoco);

		System.out.println("el texto encriptado es: "+textoEncriptado);

		String textoDesencriptado = "";
		
		textoDesencriptado = Crypto.decrypt(textoEncriptado, unSaltoLoco);

		assertTrue(textoClaro.equals(textoDesencriptado));
	}

	@Test
	void HashTest() {
		String textoClaro = "Este es un texto que todos pueden leer";

		String unSaltoLoco = "algo atado al usuario, ej UserId 20";

		String textoHasheado = "";

		textoHasheado = Crypto.hash(textoClaro, unSaltoLoco);

		System.out.println("el texto hasheado es: "+textoHasheado);

		String hashEsperado = "lxT/9Zj6PUyV/xTfCS90qfLMNEL7wnvg8VxsG/slFvZghZvQvFCZQvg584s6TMlkHqJ3wMA2J9rofsERmKGSUg==";

		assertTrue(textoHasheado.equals(hashEsperado));
	}
}

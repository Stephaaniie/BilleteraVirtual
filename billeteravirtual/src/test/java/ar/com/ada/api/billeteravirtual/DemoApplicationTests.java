package ar.com.ada.api.billeteravirtual;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.security.Crypto;
import ar.com.ada.api.billeteravirtual.services.*;

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
	@Test
	void CrearUsuarioTest() {

		Usuario usuario = usuarioService.crearUsuario("Karen", 32, 5 , "21231123", new Date(), "karen@gmail.com", "a12345");
		
		//System.out.println("SALDO de usuario: " + usuario.getPersona().getBilletera().getCuenta("ARS").getSaldo());

		//Usuario usuarioVerificado = usuarioService.buscarPorUsername(usuario.getUsername());

		//assertTrue(usuario.getUsuarioId() == usuarioVerificado.getUsuarioId());
		assertTrue(usuario.getUsuarioId()==1);
		assertTrue(usuario.getPersona().getBilletera().getCuentaPorMoneda("ARS").getSaldo().equals(new BigDecimal(500)));
	
	}

	@Test
	void EnviarSaldoTest() {
		
		Usuario usuarioEmisor = usuarioService.crearUsuario("Karen Envia", 32, 5 , "21231123", new Date(), "karenenvia@gmail.com", "a12345");
		Usuario usuarioReceptor = usuarioService.crearUsuario("Claudia Recibe", 32, 5 , "21231123", new Date(), "claudiarecibe@gmail.com", "a12345");
		
		Integer borigen = usuarioEmisor.getPersona().getBilletera().getBilleteraId();
		Integer bdestino = usuarioReceptor.getPersona().getBilletera().getBilleteraId();
		
		BigDecimal saldoOrigen = usuarioEmisor.getPersona().getBilletera().getCuentaPorMoneda("ARS").getSaldo();
		BigDecimal saldoDestino = usuarioReceptor.getPersona().getBilletera().getCuentaPorMoneda("ARS").getSaldo();

		billeteraService.enviarSaldo(borigen, usuarioEmisor.getEmail(), "ARS",new BigDecimal(1200), "PRESTAMO", "ya no me debes nada");

		BigDecimal saldoOrigenActualizado = billeteraService.consultarSaldo(borigen, "ARS");
		BigDecimal saldoDestinoActualizado = billeteraService.consultarSaldo(bdestino, "ARS");

		//AFIRMAMOS QUE, el saldo origen - 1200, sea igual al saldoOrigeActualizado
		//AFIRMAMOS QUE, el saldo destino + 1200, sea igual al saldoDestinoActualizado

		assertTrue(saldoOrigen.subtract(new BigDecimal(1200)).equals(saldoOrigenActualizado));
		assertTrue(saldoDestino.add(new BigDecimal(1200)).equals(saldoDestinoActualizado));
	}

}

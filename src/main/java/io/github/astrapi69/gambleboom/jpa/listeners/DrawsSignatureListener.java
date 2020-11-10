package io.github.astrapi69.gambleboom.jpa.listeners;

import de.alpharogroup.db.entity.verifiable.Verifiable;
import de.alpharogroup.sign.JsonSigner;
import de.alpharogroup.sign.JsonVerifier;
import de.alpharogroup.spring.autowire.AutowireAware;
import io.github.astrapi69.gambleboom.jpa.entities.Draws;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class DrawsSignatureListener
{
	@Autowired
	JsonSigner<Draws> drawsJsonSigner;
	@Autowired
	JsonVerifier<Draws> drawsJsonVerifier;

	@PrePersist
	@PreUpdate
	public void onPrePersistSign(Draws verifiable)
	{
		sign(verifiable);
	}

	@PostLoad
	@PostUpdate
	public void onPostLoad(Draws verifiable)
	{
		verify(verifiable);
	}

	private void sign(Draws verifiable)
	{
		AutowireAware.autowire(this, drawsJsonSigner);
		String digitalSignature = drawsJsonSigner.sign(verifiable);
		verifiable.setSignature(digitalSignature);
	}

	private boolean verify(Draws draws){
		AutowireAware.autowire(this, drawsJsonVerifier);
		boolean verify = drawsJsonVerifier.verify(draws, draws.getSignature());
		return verify;
	}

}

package net.guilhermejr.apilivros.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Representa o retorno da troca de senha")
public class TrocaSenhaDTO {
	
	@Schema(description = "Token de acesso", example = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZGUgTGl2cm9zIiwic3ViIjoiMSIsImp0aSI6IjEiLCJpYXQiOjE2MTc5MTM5MDcsImV4cCI6MTYxNzkxNzUwNywibm9tZSI6Ikd1aWxoZXJtZSBKci4iLCJlbWFpbCI6ImZhbGVjb21AZ3VpbGhlcm1lanIubmV0IiwiYWRtaW4iOnRydWV9.HmRp1_cI5EPont90z_1oeXrF0fvcKP0hbPgFrsy-bGQ")
	private String access_token;

}

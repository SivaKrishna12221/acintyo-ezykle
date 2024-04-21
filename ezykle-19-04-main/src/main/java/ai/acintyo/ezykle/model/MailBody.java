package ai.acintyo.ezykle.model;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
	
}

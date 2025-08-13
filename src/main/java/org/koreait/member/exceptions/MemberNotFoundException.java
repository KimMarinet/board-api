package org.koreait.member.exceptions;

import org.koreait.global.exceptions.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(String message) {

      super("NotFound.member");
      setErrorCode(true);
    }
}

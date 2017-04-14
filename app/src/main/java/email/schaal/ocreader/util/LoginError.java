package email.schaal.ocreader.util;

import android.content.Context;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLHandshakeException;

import email.schaal.ocreader.R;

/**
 * Turn login errors into human-readable strings
 */
public class LoginError {
    public enum Section {
        URL,
        USER,
        PASSWORD,
        NONE,
        UNKNOWN
    }

    private final Section section;
    private final String message;
    private final Throwable throwable;

    public LoginError(String message) {
        this(Section.NONE, message, null);
    }

    public LoginError(Section section, String message) {
        this(section, message, null);
    }

    private LoginError(Section section, String message, Throwable throwable) {
        this.section = section;
        this.message = message;
        this.throwable = throwable;
    }

    private LoginError(Throwable throwable) {
        this(Section.UNKNOWN, throwable.getMessage(), throwable);
    }

    public Section getSection() {
        return section;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public static LoginError getError(Context context, int code, String defaultMessage) {
        switch (code) {
            case 401:
                return new LoginError(Section.USER, context.getString(R.string.error_incorrect_username_or_password));
            case 403:
            case 404:
                return new LoginError(Section.URL, context.getString(R.string.error_oc_not_found));
            case 405:
                return new LoginError(Section.URL, context.getString(R.string.ncnews_too_old));
            default:
                return new LoginError(context.getString(R.string.http_error, code) + ": " + defaultMessage);
        }
    }

    public static LoginError getError(Context context, Throwable t) {
        if (t instanceof UnknownHostException) {
            return new LoginError(Section.URL, context.getString(R.string.error_unknown_host));
        } else if (t instanceof SSLHandshakeException) {
            if (t.getCause() instanceof CertificateException) {
                return new LoginError(Section.URL, context.getString(R.string.untrusted_certificate));
            }
        } else if (t instanceof ConnectException) {
            return new LoginError(Section.URL, context.getString(R.string.could_not_connect));
        } else if(t instanceof IOException) {
            return new LoginError(Section.NONE, context.getString(R.string.ncnews_too_old));
        }
        return new LoginError(t);
    }
}

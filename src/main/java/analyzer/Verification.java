package analyzer;

public class Verification {
    private String token;
    private String challenge;
    private String type;

    public Verification() {

    }

//	public Verification(String challenge) {
//		this.challenge = challenge;
//	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}


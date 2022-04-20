public interface Constants {
    String RU = "\uD83C\uDDF7\uD83C\uDDFA Русский";
    String UZ = "\uD83C\uDDFA\uD83C\uDDFF O'zbek";
    String EN = "\uD83C\uDDEC\uD83C\uDDE7 English";
    String TR = "\uD83C\uDDF9\uD83C\uDDF7 Türk";
    String tool = " ↔ ";

    static String getLang(String string) {
        switch (string.toLowerCase()) {
            case "ru" -> {
                return RU;
            }
            case "en" -> {
                return EN;
            }
            case "tr" -> {
                return TR;
            }
            case "uz" -> {
                return UZ;
            }
            default -> {
                return "";
            }
        }
    }
}

package com.csc498g.bliss;

public class Constants {

    static class Users {

        public static final String USER_ID = "user_id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String BIRTHDAY = "birthday";
        public static final String JOIN = "join_date";
        public static final String GENDER = "gender";
        public static final String PICTURE = "picture";
        public static final String BANNER = "banner";
        public static final String FOLLOWINGS = "followings_TEMP";
        public static final String FOLLOWERS = "followers_TEMP";

    }

    static class Gems {

        public static final String GEM_ID = "gem_id";
        public static final String MINE_DATE = "mine_date";
        public static final String EDIT_DATE = "edit_date";
        public static final String CONTENT = "content";
        public static final String TYPE = "type";
        public static final String OWNER_ID = "owner_id";
        public static final String REMINES = "remines_TEMP";
        public static final String DIAMONDS = "diamonds_TEMP";
        public static final String IS_DIAMONDED = "is_diamonded";

        static class Content {

            public static final String TEXT = "text";
            public static final String IMG_SRC = "img_src";
            public static final String VID_SRC = "vid_src";
            public static final String OPTION1 = "option1";
            public static final String OPTION2 = "option2";
            public static final String OPTION3 = "option3";
            public static final String OPTION4 = "option4";
            public static final String OPTION1PERC = "option1voters";
            public static final String OPTION2PERC = "option2voters";
            public static final String OPTION3PERC = "option3voters";
            public static final String OPTION4PERC = "option4voters";
            public static final String PROMPT = "prompt";

        }

    }

    static class Remines {

        public static final String USER_ID = "user_id";
        public static final String GEM_ID = "gem_id";
        public static final String REMINE_DATE = "remine_date";

    }

    static class Follows {

        public static final String USER_ID1 = "user_id1";
        public static final String USER_ID2 = "user_id2";
    }

    static class Diamonds {

        public static final String USER_ID = "user_id";
        public static final String GEM_ID = "gem_id";
        public static final String DIAMOND_DATE = "diamond_date";

    }

    static class Answers {

        public static final String USER_ID = "user_id";
        public static final String GEM_ID = "gem_id";
        public static final String ANSWER_DATE = "answer_date";
        public static final String OPTION_CHOSEN = "option_chosen";


    }

    static class APIs {

        public static final String ADD_GEM = "add_gem";
        public static final String ADD_USER = "add_user";
        public static final String AUTHENTICATE_LOGIN = "authenticate_login";
        public static final String DIAMOND_GEM = "diamond_gem";
        public static final String UNDIAMOND_GEM = "undiamond_gem";
        public static final String FOLLOW_USER = "follow_user";
        public static final String GET_ALL_DIAMONDS_ON_GEM = "get_all_diamonds_on_gem";
        public static final String GET_ALL_GEMS = "get_all_gems";
        public static final String GET_ALL_IMAGE_GEMS = "get_all_image_gems";
        public static final String GET_ALL_POLL_GEMS = "get_all_poll_gems";
        public static final String GET_ALL_TEXT_GEMS = "get_all_text_gems";
        public static final String GET_ALL_USERS = "get_all_users";
        public static final String GET_ALL_VIDEO_GEMS = "get_all_video_gems";
        public static final String GET_FOLLOWERS = "get_followers";
        public static final String GET_FOLLOWINGS = "get_followings";
        public static final String GET_USER = "get_user";
        public static final String IS_EMAIL_AVAILABLE = "is_email_available";
        public static final String IS_USERNAME_EMAIL_AVAILABLE = "is_username_email_available";
        public static final String IS_USERNAME_AVAILABLE = "is_username_available";
        public static final String GET_ALL_GEMS_BY_USER = "get_all_gems_by_user";


    }

    static class Response {

        public static final String ERROR = "error";
        public static final String SUCCESS = "success";
        public static final String IS_AUTHENTICATED = "is_authenticated";
        public static final String QUERY_RESULT = "query_result";
        public static final String IS_AVAILABLE = "is_available";
        public static final String LAST_ID = "inserted_id";
        public static final String CLASS = "class";
    }

    static class Availability {

        public static final int NONE_AVAILABLE = 0;
        public static final int USERNAME_AVAILABLE = 1;
        public static final int EMAIL_AVAILABLE = 2;
        public static final int ALL_AVAILABLE = 3;


    }

    static class Classes {

        public static final String USER = "user";
        public static final String GEM = "gem";
        public static final String DIAMOND = "diamond";
        public static final String FOLLOWING = "following";
        public static final String FOLLOWER = "follower";
        public static final String VIDEO = "video_gem";
        public static final String TEXT = "text_gem";
        public static final String POLL = "poll_gem";
        public static final String IMAGE = "image_gem";

    }

    static class URL {

        public static final String MASTER_URL = "http://10.31.207.111/Bliss/Bliss_server/";
        public static String buildUrl(String API) {
            return MASTER_URL + API + ".php";
        }

    }



}

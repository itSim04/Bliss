package com.csc498g.bliss;

public class Constants {

    // Activity used for cleaner linking between the Client and the Server

    static class Users {

        // The Users column in the database

        public static final String USER_ID = "user_id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String BIO = "bio";
        public static final String BIRTHDAY = "birthday";
        public static final String JOIN = "join_date";
        public static final String GENDER = "gender";
        public static final String PICTURE = "picture";
        public static final String BANNER = "banner";
        public static final String FOLLOWINGS = "followings_TEMP";
        public static final String FOLLOWERS = "followers_TEMP";

    }

    static class Gems {

        // The Gems columns in the database

        public static final String GEM_ID = "gem_id";
        public static final String MINE_DATE = "mine_date";
        public static final String EDIT_DATE = "edit_date";
        public static final String CONTENT = "content";
        public static final String TYPE = "type";
        public static final String OWNER_ID = "owner_id";
        public static final String REMINES = "remines_TEMP";
        public static final String DIAMONDS = "diamonds_TEMP";
        public static final String COMMENTS = "comments_TEMP";
        public static final String ROOT = "root_id";
        public static final String IS_DIAMONDED = "is_diamonded";
        public static final String OPTION = "is_voted";

        static class Content {

            // All possible objects contained in the Content column

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

        // The Remines columns in the database (WIP)

        public static final String USER_ID = "user_id";
        public static final String GEM_ID = "gem_id";
        public static final String REMINE_DATE = "remine_date";

    }

    static class Follows {

        // The Follows columns in the database

        public static final String USER_ID1 = "user_id1";
        public static final String USER_ID2 = "user_id2";
        public static final String FOLLOW_DATE = "follow_date";
    }

    static class Diamonds {

        // The Diamonds columns in the database

        public static final String USER_ID = "user_id";
        public static final String GEM_ID = "gem_id";
        public static final String DIAMOND_DATE = "diamond_date";

    }

    static class Answers {

        // The Answers columns in the database

        public static final String USER_ID = "user_id";
        public static final String GEM_ID = "gem_id";
        public static final String ANSWER_DATE = "answer_date";
        public static final String OPTION_CHOSEN = "option_chosen";


    }

    static class APIs {

        // All the APIs of the app

        public static final String ADD_GEM = "add_gem"; // Adds a Gem to the database
        public static final String ADD_USER = "add_user"; // Adds a User to the database
        public static final String UPDATE_USER = "update_user"; // Updates a User in the database
        public static final String AUTHENTICATE_LOGIN = "authenticate_login"; // Authenticates a login
        public static final String DIAMOND_GEM = "diamond_gem"; // Diamonds a Gem in the database
        public static final String UNDIAMOND_GEM = "undiamond_gem"; // Removes a Diamond from the database
        public static final String FOLLOW_USER = "follow_user"; // Follows a User in the database
        public static final String GET_ALL_DIAMONDS_ON_GEM = "get_all_diamonds_on_gem"; // Gets all Diamonds on a Gem (WIP)
        public static final String GET_ALL_GEMS = "get_all_gems"; // Gets all Gems
        public static final String GET_ALL_USERS = "get_all_users"; // Gets all Users (WIP)
        public static final String GET_FOLLOWERS = "get_followers"; // Get all followers (WIP)
        public static final String GET_FOLLOWINGS = "get_followings"; // Get all followings (WIP)
        public static final String GET_USER = "get_user"; // Gets a user from the database
        public static final String IS_USERNAME_EMAIL_AVAILABLE = "is_username_email_available"; // Checks if the email and username are available
        public static final String GET_ALL_GEMS_BY_USER = "get_all_gems_by_user"; // Gets all gems belonging to a user
        public static final String DELETE_GEM = "delete_gem"; // Removes a gem from the database
        public static final String ANSWER_POST = "answer_post"; // Answers a poll in the database
        public static final String UNFOLLOW_USER = "unfollow_user"; // Removes a Follow entry from the database
        public static final String IS_FOLLOWING = "is_following"; // Checks if a user is following another
    }

    static class Response {

        // The response object returned by APIs

        public static final String ERROR = "error"; // The error code (if any)
        public static final String SUCCESS = "success"; // The success status
        public static final String IS_AUTHENTICATED = "is_authenticated"; // Whether an authentication was successful
        public static final String QUERY_RESULT = "query_result"; // The results of a query
        public static final String IS_AVAILABLE = "is_available"; // Whether an availability has been detected
        public static final String LAST_ID = "inserted_id"; // The last inserted id

        static class Availability {

            // All possible Keys in an availability check

            public static final int NONE_AVAILABLE = 0;
            public static final int USERNAME_AVAILABLE = 1;
            public static final int EMAIL_AVAILABLE = 2;
            public static final int ALL_AVAILABLE = 3;

        }

        static class Classes {

            // All possible keys in a Query Result

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


    }


    static class URL {

        // The URL center

        public static final String MASTER_URL = "http://bliss.great-site.net/Bliss_server/";
        public static String buildUrl(String API) {
            return MASTER_URL + API + ".php";
        }

    }



}

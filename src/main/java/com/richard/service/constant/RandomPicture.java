package com.richard.service.constant;

import java.util.Random;

public class RandomPicture {
    private static String picture[] = {
            "http://oow561q5i.bkt.clouddn.com/random/003e9fe092534b3625230ef4fa4dc602.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/0593b2170ffae0622f0a7ee2b2df4516.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/460667e921907c5e3bc303dbe7a8fb60.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/47142ca871144857c2eee5e5f3b477b3.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/4794f83b237cc5cfbb92452d209e4fc6.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/59bb9168c31112e906bd62628ec6055e.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/61f5df2c8278bf849466bb60b62efc28.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/77944494e95b20aeb89f3941764c2259.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/95b87ea3674767d1af4457a5274744d0.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/9739a6c649ad08ea9cf14e6ba4e6104b.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/ad81c14c31a7a4d732a302f5c4c594a9.jpg",
            "http://oow561q5i.bkt.clouddn.com/random/6e310e1c3b3083a1395d13b25b505817.jpg",
    };

    public static String getRandomPicture(){
        Random rand = new Random();
        return picture[rand.nextInt(picture.length)];
    }
}

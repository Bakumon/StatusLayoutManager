package me.bakumon.statuslayoutmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 列表数据提供
 *
 * @author Bakumon https://bakumon.me
 * @date 2017/12/22
 */
public class SimpleData {

    public static class Song {
        public Song(String songName, int drawableResID) {
            this.songName = songName;
            this.drawableResID = drawableResID;
        }

        public String songName;
        public int drawableResID;
    }

    public static List<Song> getRandomSonList(int amount) {
        ArrayList<Song> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(new Song(SONG_NAMES[random.nextInt(SONG_NAMES.length)],
                    SONG_DRAWABLE[random.nextInt(SONG_DRAWABLE.length)]));
        }
        return list;
    }

    private static final int[] SONG_DRAWABLE = {
            R.drawable.jay_1, R.drawable.jay_2, R.drawable.jay_3, R.drawable.jay_4, R.drawable.jay_5
    };

    private static final String[] SONG_NAMES = {
            "告白气球", "晴天", "稻香", "七里香", "算什么男人",
            "搁浅", "最长的电影", "开不了口", "彩虹", "不能说的秘密", "甜甜的",
            "一路向北", "简单爱", "回到过去", "说好的幸福呢", "安静",
            "夜曲", "枫", "退后", "青花瓷", "烟花易冷",
            "蒲公英的约定", "给我一首歌的时间", "明明就", "龙卷风", "以父之名",
            "半岛铁盒", "发如雪", "珊瑚海", "爱情废柴", "轨迹", "借口",
            "黑色毛衣", "听妈妈的话", "不该", "我不配", "说了再见", "星晴", "手写的从前",
            "东风破", "可爱女人", "夜的第七章", "蜗牛🐌", "世界末日",
            "红尘客栈", "爱在西元前", "听见下雨的声音", "屋顶", "一点点", "园游会"
    };

}

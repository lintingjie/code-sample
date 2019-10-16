package com.example.demo.utils;

import com.vanke.core.util.DataUtil;
import util.model.CityBoundary;
import util.model.Poi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lintingjie
 * @since 2019/8/16
 */
public class BoundaryUtil {

    static List<CityBoundary> boundaryList = new ArrayList<>();

    static {
        CityBoundary xiamen = new CityBoundary();
        xiamen.setName("厦门市");
        xiamen.setCode("350200");
        xiamen.setBottomLeft(new Poi(24.389041, 117.891838));
        xiamen.setUpperRight(new Poi(24.915854, 118.462909));

        CityBoundary shenzhen = new CityBoundary();
        shenzhen.setName("深圳市");
        shenzhen.setCode("440300");
        shenzhen.setBottomLeft(new Poi(22.455558, 113.757567));
        shenzhen.setUpperRight(new Poi(22.841983, 114.604655));

        CityBoundary guangzhou = new CityBoundary();
        guangzhou.setName("广州市");
        guangzhou.setCode("440100");
        guangzhou.setBottomLeft(new Poi(22.598196, 112.991107));
        guangzhou.setUpperRight(new Poi(23.924419, 114.063332));

        CityBoundary dongguan = new CityBoundary();
        dongguan.setName("东莞市");
        dongguan.setCode("441900");
        dongguan.setBottomLeft(new Poi(22.661484, 113.528657));
        dongguan.setUpperRight(new Poi(23.149034, 114.265529));

        CityBoundary foshan = new CityBoundary();
        foshan.setName("佛山市");
        foshan.setCode("440600");
        foshan.setBottomLeft(new Poi(22.651013, 112.397536));
        foshan.setUpperRight(new Poi(23.578044, 113.398197));

        CityBoundary changsha = new CityBoundary();
        changsha.setName("长沙市");
        changsha.setCode("430100");
        changsha.setBottomLeft(new Poi(27.863669, 111.913028));
        changsha.setUpperRight(new Poi(28.660108, 114.226277));

        CityBoundary fuzhou = new CityBoundary();
        fuzhou.setName("福州市");
        fuzhou.setCode("350100");
        fuzhou.setBottomLeft(new Poi(25.332789, 118.435397));
        fuzhou.setUpperRight(new Poi(26.636736, 119.95885));

        CityBoundary zhuhai = new CityBoundary();
        zhuhai.setName("珠海市");
        zhuhai.setCode("440400");
        zhuhai.setBottomLeft(new Poi(21.785005, 113.065796));
        zhuhai.setUpperRight(new Poi(22.461802, 114.41986));

        CityBoundary zhongshan = new CityBoundary();
        zhongshan.setName("中山市");
        zhongshan.setCode("442000");
        zhongshan.setBottomLeft(new Poi(22.181868, 113.164981));
        zhongshan.setUpperRight(new Poi(22.783174, 113.699814));

        CityBoundary nanning = new CityBoundary();
        nanning.setName("南宁市");
        nanning.setCode("450100");
        nanning.setBottomLeft(new Poi(22.25529, 107.392601));
        nanning.setUpperRight(new Poi(24.006326, 109.62564));

        CityBoundary sanya = new CityBoundary();
        sanya.setName("三亚市");
        sanya.setCode("460200");
        sanya.setBottomLeft(new Poi(18.121885, 108.938477));
        sanya.setUpperRight(new Poi(18.62931, 109.817277));

        boundaryList.add(xiamen);
        boundaryList.add(shenzhen);
        boundaryList.add(guangzhou);
        boundaryList.add(dongguan);
        boundaryList.add(foshan);
        boundaryList.add(changsha);
        boundaryList.add(fuzhou);
        boundaryList.add(zhuhai);
        boundaryList.add(zhongshan);
        boundaryList.add(nanning);
        boundaryList.add(sanya);
    }

    /**
     * 经纬度是否在该城市边界内
     * @param city
     * @param poi
     * @return
     */
    public static Boolean isInBoundary(String city, Poi poi){
        if(DataUtil.isEmpty(city) || DataUtil.isEmpty(poi) || DataUtil.isEmpty(poi.getLat()) || DataUtil.isEmpty(poi.getLng())){
            return false;
        }
        CityBoundary cityBoundary = boundaryList.stream().filter(b-> Objects.equals(b.getCode(), city)).findFirst().orElse(null);
        if(cityBoundary != null){
            Poi bottomLeft = cityBoundary.getBottomLeft();
            Poi upRight = cityBoundary.getUpperRight();
            if(poi.getLng() >= bottomLeft.getLng() && poi.getLat() >= bottomLeft.getLat() && poi.getLng() <= upRight.getLng() && poi.getLat() <= upRight.getLat()){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(isInBoundary("350200", new Poi(24.547757, 118.112537)));
    }



}

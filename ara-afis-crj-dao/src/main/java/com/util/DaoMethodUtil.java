package com.util;

import com.model.FpTemplate;
import com.vo.FpImageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 文件名：DaoMethodUtil
 * 作者：tree
 * 时间：2017/6/19
 * 描述：
 * 版权：亚略特
 */
public class DaoMethodUtil {

    private static Logger logger = LoggerFactory.getLogger(DaoMethodUtil.class);

    /**
     * 对两个List数据进行筛选
     * @param imageList 源数据库的数据集
     * @param templateList 比对数据库的数据集
     * @return List<FpTemplate>  筛选出来的不同的列表
     */
    @SuppressWarnings("rawtypes")
    public static List<FpTemplate> compareDifferList(List<FpImageSource> imageList, List<FpTemplate> templateList){
        List<FpTemplate> differList = new ArrayList<>();
        List<FpTemplate> sameList = new ArrayList<>();
        //imageList 和 templateList健壮性判断
        if(Optional.of(imageList).isPresent() && Optional.of(templateList).isPresent()  && imageList.size() > 0){
            logger.debug("---------源图像库数据量[SrcData NUM]："+imageList.size());
            logger.debug("---------指纹库数据量[DesData NUM]："+templateList.size());
            //筛选出来相同的元素
            imageList.stream().forEach(x -> templateList.stream().filter(y -> y.getPersonId().equals(x.getPersonId()) && y.getIndex() == x.getIndex() && y.getLibrary().equals(x.getLibrary())).forEach(sameList::add));
        }

        logger.debug("---------相同的数据量[SameData NUM]："+sameList.size());

        //筛选出来比对库中不同的元素,构造后加入不同列表
        templateList.stream().filter(x -> !sameList.contains(x)).forEach(x -> {
            x.setDesHaveFlag("有");
            x.setSrcHaveFlag("无");
            differList.add(x);
        });
        logger.debug("---------目标库不同的数据量[DesDifData NUM]："+differList.size());
        //筛选出来源库中不同的元素,构造后加入不同列表
        imageList.stream().filter(x -> {
            boolean flag = true;
            for(FpTemplate template:sameList){
                if(template.getPersonId().equals(x.getPersonId()) && template.getIndex() == x.getIndex() && template.getLibrary().equals(x.getLibrary())){
                    flag = false;
                }
            }
            return flag;
        }).forEach(x -> {
            FpTemplate template = new FpTemplate();
            template.setPersonId(x.getPersonId());
            template.setIndex(x.getIndex());
            template.setLibrary(x.getLibrary());
            template.setDesHaveFlag("无");
            template.setSrcHaveFlag("有");
            differList.add(template);
        });
        logger.debug("---------总的不同的数据量[SrcDifData NUM]："+differList.size());
        return differList;
    }
}

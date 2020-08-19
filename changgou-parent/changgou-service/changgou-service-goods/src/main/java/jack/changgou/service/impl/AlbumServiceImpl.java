package jack.changgou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jack.changgou.dao.AlbumMapper;
import jack.changgou.goods.pojo.Album;
import jack.changgou.goods.pojo.Brand;
import jack.changgou.service.AlbumService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<Album> getAllAlbum() {
        return albumMapper.selectAll();
    }

    @Override
    public Album getAlbumById(Integer id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addAlbum(Album album) {
        albumMapper.insertSelective(album);
    }

    @Override
    public void modefyAlbum(Album album) {
        albumMapper.updateByPrimaryKeySelective(album);
    }

    @Override
    public void deleteAlbumById(Integer id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Album> searchAlbum(Album album) {
        return albumMapper.selectByExample(createCriteria(album));
    }


    /**
     * 创建搜索条件
     *
     * @param album
     * @return
     */
    public Example createCriteria(Album album) {

        // 创建条件构造器
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 按照相册名模糊查询
        if (!StringUtils.isEmpty(album.getTitle())) {
            criteria.andLike("name", "%" + album.getTitle() + "%");// 搜索条件
        }
        return example;
    }

    @Override
    public PageInfo<Album> getAllAlbumPaged(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Album> albums = albumMapper.selectAll();
        return new PageInfo<>(albums);
    }

    @Override
    public PageInfo<Album> searchAlbumPaged(Album album, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Album> albums = albumMapper.selectByExample(createCriteria(album));
        return new PageInfo<>(albums);
    }
}

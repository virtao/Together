package main

import (
	"bytes"
	"container/list"
	"encoding/binary"
	"sync"
)

type dataUnit struct {
	StartX     int32
	StartY     int32
	EndX       int32
	EndY       int32
	StartColor uint32
	EndColor   uint32
	Duration   uint32
}

type CacheControl struct {
	data      *list.List
	mutex     *sync.Mutex
	cacheSize int
}

func NewCacheControl() (ret *CacheControl) {
	ret = new(CacheControl)
	ret.data = new(list.List)
	ret.cacheSize = 65535
	return ret
}

func (cc *CacheControl) AddElement(data *dataUnit) {
	cc.mutex.Lock()
	defer cc.mutex.Unlock()
	cc.data.PushBack(data)
	if cc.data.Len() > cc.cacheSize {
		cc.data.Remove(cc.data.Front())
	}
}

func (cc *CacheControl) GetElement() (data *dataUnit) {
	cc.mutex.Lock()
	defer cc.mutex.Unlock()
	if cc.data.Len() > 0 {
		data, _ = cc.data.Remove(cc.data.Front()).(*dataUnit)
		return data
	} else {
		return nil
	}

}

func (d *dataUnit) toBytes() (ret []byte) {

}

func (d *dataUnit) fromBytes(data []byte) (err error) {

	}
}

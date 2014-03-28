package main

import (
	"bytes"
	"encoding/hex"
	"fmt"
	"time"
	//"io/ioutil"
	"bufio"
	"encoding/binary"
	"errors"
	"flag"
	"io"
	"net"
	"os"
	"strings"
)

var cc *CacheControl
var listenAddr string

func main() {
	cc = NewCacheControl()

	flag.StringVar(&listenAddr, "i", "127.0.0.1:16200", "指定监听IP和端口。")
	//flag.StringVar(&remote_server, "r", "", "远程服务器IP和端口。")
	flag.Parse()

	if err := startServer(listenAddr); err != nil {
		fmt.Println(err.Error())
	}
}

func startServer(addr string) (err error) {
	var udpAddr *net.UDPAddr
	var udpConn *net.UDPConn

	if udpAddr, err = net.ResolveUDPAddr("udp4", addr); err != nil {
		return err
	}

	if udpConn, err = net.ListenUDP("udp", udpAddr); err != nil {
		return err
	}

	for {
		buf = make([]byte, 128)
	if n, addr, err := conn.ReadFromUDP(buf); err != nil {
		//fmt.Println(err.Error())
		return err
	} else {
		go recvDataParse(udpConn, addr, buf[0:n])
	}
}



// Request command.
const CMD_GET_DATA = 1
const CMD_SEND_DATA = 2

// Response command.
const CMD_RESULT = 3
const CMD_EMPTY = 4

func recvDataParse(conn *net.UDPConn, addr string, data []byte) {
	cmd := data[0]
	switch cmd {
	case CMD_GET_DATA:
		
	case CMD_SEND_DATA:

	default:
		return "", errors.New("错误的搜索命令。")
	}
}


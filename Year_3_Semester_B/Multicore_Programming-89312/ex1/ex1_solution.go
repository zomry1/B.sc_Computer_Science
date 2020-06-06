package main

import (
	"fmt"
	"os"
	"strings"
	"time"
)

func main() {
	section1()
	section2()
	section3()
}


//****** Exercise1.1 ******//
func section1() {
	fmt.Println(strings.Join(os.Args[0:], " "))
}

//****** Exercise1.2 ******//
func section2() {
	for i := 1; i < len(os.Args); i++ {
		fmt.Printf("%d %s\n", i ,os.Args[i])
	}
}

//****** Exercise1.3 ******//
func section3() {
	var echo1Time time.Duration
	var echo2Time time.Duration
	var echo3Time time.Duration
	//var start float64

	//echo1 - Run 3000 times
	for i := 1; i < 3000; i++ {
		start := time.Now()
		var s, sep string
		for j := 1; j < len(os.Args); j++ {
			s += sep + os.Args[j]
			sep = " "
		}
		echo1Time += time.Since(start)
		//fmt.Println(s)
	}

	//echo2 - Run 3000 times
	for i := 1; i < 3000; i++ {
		start := time.Now()
		s, sep := "", ""
		for _, arg := range os.Args[1:] {
			s += sep + arg
			sep = " "
		}
		echo2Time += time.Since(start)
	}

	//echo3 - Run 3000 times
	for i := 1; i < 3000; i++ {
		start := time.Now()
		arg := strings.Join(os.Args[1:], " ")
		echo3Time += time.Since(start)
		arg += "" //Need to use the arg to avoid compile error, use it after the time calculation
	}

	//Get average time
	echo1Time = echo1Time / 3000
	echo2Time = echo2Time / 3000
	echo3Time = echo3Time / 3000

	fmt.Printf("echo 1 %ss elapsed\necho 2 %ss elapsed\necho 3 %ss elapsed\n", echo1Time,echo2Time,echo3Time)


}

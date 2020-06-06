package main

import (
	"fmt"
	"net/http"
	"os"
	"bufio"
	"io"
	"io/ioutil"
)

func main() {
	section1()
	//section2()
	//section3()
}


//****** Exercise 1.4 (1) ******//
func section1() {
	counts := make(map[string]int)
	fileMap := make(map[string][]string)
	files := os.Args[1:]
	if len(files) == 0 {
		countLines(os.Stdin, counts, fileMap)
	} else {
		for _, arg := range files {
			f, err := os.Open(arg)
			if err != nil {
				fmt.Fprintf(os.Stderr, "dup2: %v\n", err)
				continue
			}
			countLines(f, counts, fileMap)
			f.Close()
		}
	}
	for line, n := range counts {
		if n > 1 {
			fmt.Printf("%d\t%s\t%v\n", n, line, fileMap[line])
		}
	}
}

func AppendIfMissing(slice []string, s string) []string {
    for _, element := range slice {
        if element == s {
            return slice
        }
    }
    return append(slice, s)
}

func countLines(f *os.File, counts map[string]int, fileMap map[string][]string) {
	input := bufio.NewScanner(f)
	for input.Scan() {
		line := input.Text()
		counts[line]++
		fileMap[line] = AppendIfMissing(fileMap[line], f.Name())
	}
}

//****** Exercise 1.7 (2) ******//
func section2() {
	for _, url := range os.Args[1:] {
		resp, err := http.Get(url)
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: %v\n", err)
			os.Exit(1)
		}
		_, err = io.Copy(os.Stdout, resp.Body)
		resp.Body.Close()
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: reading %s: %v\n", url, err)
			os.Exit(1)
		}
	}
}

//****** Exercise 1.9 (3) ******//
func section3() {
	for _, url := range os.Args[1:] {
		resp, err := http.Get(url)
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: %v\n", err)
			os.Exit(1)
		}
		b, err := ioutil.ReadAll(resp.Body)
		resp.Body.Close()
		if err != nil {
			fmt.Fprintf(os.Stderr, "fetch: reading %s: %v\n", url, err)
			os.Exit(1)
		}
		fmt.Printf("%s\nStatus-code: %s\n", b, resp.Status)
	}
}

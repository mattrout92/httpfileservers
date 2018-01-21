package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"os"
	"regexp"

	"github.com/gorilla/mux"
)

func main() {
	router := mux.NewRouter()

	router.Path("/download/{uri:.*}").Methods("GET").HandlerFunc(download)
	router.Path("/upload/{uri:.*}").Methods("POST").HandlerFunc(upload)
	router.Path("/metadata/{uri:.*}").Methods("GET").HandlerFunc(metadata)

	if err := http.ListenAndServe(":3030", router); err != nil {
		panic(err)
	}
}

func download(w http.ResponseWriter, req *http.Request) {
	path := req.URL.Path
	reg := regexp.MustCompile(`^\/download\/(.+)$`)

	params := reg.FindStringSubmatch(path)
	filename := params[1]

	b, err := ioutil.ReadFile(filename)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		fmt.Println(err.Error())
		return
	}

	w.Write(b)
}

func upload(w http.ResponseWriter, req *http.Request) {
	path := req.URL.Path
	reg := regexp.MustCompile(`^\/upload\/(.+)$`)

	params := reg.FindStringSubmatch(path)
	filename := params[1]

	defer req.Body.Close()
	b, err := ioutil.ReadAll(req.Body)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		fmt.Println(err.Error())
		return
	}

	f, err := os.Create(filename)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		fmt.Println(err.Error())
		return
	}
	defer f.Close()

	f.Write(b)
}

// FileStats ...
type FileStats struct {
	Name string `json:"name"`
	Size int64  `json:"size"`
}

func metadata(w http.ResponseWriter, req *http.Request) {
	path := req.URL.Path
	reg := regexp.MustCompile(`^\/metadata\/(.+)$`)

	params := reg.FindStringSubmatch(path)
	filename := params[1]

	stats, err := os.Stat(filename)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		fmt.Println(err.Error())
		return
	}

	fs := FileStats{
		Name: stats.Name(),
		Size: stats.Size(),
	}

	b, err := json.Marshal(fs)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		fmt.Println(err.Error())
		return
	}

	w.Write(b)

}

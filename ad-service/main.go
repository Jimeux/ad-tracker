package main

import (
	_ "github.com/lib/pq"
	"github.com/garyburd/redigo/redis"
	"github.com/gin-gonic/gin"
	"github.com/go-xorm/xorm"
	"time"
)

func initCache() *redis.Pool {
	return &redis.Pool{
		MaxIdle:     3,
		IdleTimeout: 240 * time.Second,
		Dial: func() (redis.Conn, error) {
			return redis.Dial("tcp", "127.0.0.1:6380")
		},
	}
}

func initDb() *xorm.Engine {
	db, err := xorm.NewEngine("postgres", "postgresql://default:default@127.0.0.1:5435/todos?sslmode=disable")
	if err != nil {
		panic("database could not be initialised: " + err.Error())
	}
	db.ShowSQL(true)
	return db
}

func main() {
	db := initDb()
	cache := initCache()

	router := gin.Default()

	defer cache.Close()
	defer db.Close()

	router.Run()
}

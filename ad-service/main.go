package main

import (
	_ "github.com/lib/pq"
	"github.com/garyburd/redigo/redis"
	"github.com/gin-gonic/gin"
	"github.com/go-xorm/xorm"
	"time"
	"github.com/Jimeux/ad-tracker/ad-service/ads"
)

func initCache() *redis.Pool {
	return &redis.Pool{
		MaxIdle:     3,
		IdleTimeout: 240 * time.Second,
		Dial: func() (redis.Conn, error) {
			return redis.Dial(
				"tcp",
				"127.0.0.1:6380",
			)
		},
	}
}

func initDb() *xorm.Engine {
	db, err := xorm.NewEngine(
		"postgres",
		"postgresql://default:default@localhost:5432/ad_management?sslmode=disable",
	)
	if err != nil {
		panic("database could not be initialised: " + err.Error())
	}
	db.ShowSQL(true)
	return db
}

func main() {
	db := initDb()
	cache := initCache()

	adRepository := ad.NewRepository(db)
	adHandler := ad.NewHandler(adRepository)

	router := gin.Default()

	initializeRoutes(router, adHandler)

	defer cache.Close()
	defer db.Close()

	router.Run()
}

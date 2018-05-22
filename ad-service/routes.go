package main

import (
	"github.com/gin-gonic/gin"
	"github.com/Jimeux/ad-tracker/ad-service/ads"
)

func initializeRoutes(
	router *gin.Engine,
	adHandler *ad.Handler,
) {

	router.POST("/v1/ads", adHandler.Create)

}


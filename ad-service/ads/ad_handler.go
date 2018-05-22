package ad

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

type Handler struct {
	repository Repository
}

func NewHandler(repository Repository) *Handler {
	return &Handler{repository}
}

func (h *Handler) Create(context *gin.Context) {
	command := CreateCommand{}
	context.Bind(&command) // TODO: Check errors

	ad, err := h.repository.Create(command)

	if err != nil {
		context.AbortWithStatus(http.StatusInternalServerError)
	} else {
		context.JSON(http.StatusOK, ad)
	}
}

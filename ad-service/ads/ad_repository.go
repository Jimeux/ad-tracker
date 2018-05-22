package ad

import (
	"github.com/go-xorm/xorm"
	"time"
)

type Repository interface {
	Create(command CreateCommand) (*Model, error)
}

func NewRepository(db *xorm.Engine) Repository {
	return &RepositoryImpl{db}
}

type RepositoryImpl struct {
	db *xorm.Engine
}

func (r *RepositoryImpl) Create(command CreateCommand) (*Model, error) {
	ad := Model{
		Name:    command.Name,
		Content: command.Content,
		Created: time.Now(),
		Deleted: false,
	}
	_, err := r.db.Insert(&ad)
	return &ad, err
}

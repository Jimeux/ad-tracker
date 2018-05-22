package ad

import "time"

type Model struct {
	Id      int64     `json:"id" xorm:"SERIAL pk"`
	Name    string    `json:"name" xorm:"not null"`
	Content string    `json:"content" xorm:"not null"`
	Created time.Time `json:"created" xorm:"not null default CURRENT_TIMESTAMP"`
	Deleted bool      `json:"-" xorm:"not null default false"`
}

func (a Model) TableName() string {
	return "ads"
}

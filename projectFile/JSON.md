# Общие договорённости (важно зафиксировать)

### Формат

* JSON
* UTF-8
* `snake_case`

### Даты и время

* `date`: `"YYYY-MM-DD"`
* `time`: `"HH:mm"`
* `datetime`: `"YYYY-MM-DDTHH:mm"`

### Статусы записи

```json
"booked" | "canceled" | "completed"
```

---

# Пользователи и роли (для UI авторизации)

## GET `/api/me`

> кто сейчас залогинен (для показа UI по ролям)

```json
{
  "user_id": 3,
  "fio": "Иванов Иван Иванович",
  "role": "registrar"
}
```

---

# Врачи

## GET `/api/doctors`

> список врачей (выпадающий список в UI)

```json
[
  {
    "doctor_id": 1,
    "fio": "Петров Пётр Петрович",
    "specialty": "Терапевт"
  },
  {
    "doctor_id": 2,
    "fio": "Сидоров Алексей Николаевич",
    "specialty": "Хирург"
  }
]
```

---

# Пациенты

## GET `/api/patients`

```json
[
  {
    "patient_id": 10,
    "fio": "Иванов Иван",
    "birth_year": 2001
  }
]
```

---

## POST `/api/patients`

```json
{
  "fio": "Смирнова Анна",
  "birth_year": 2003
}
```

---

# Рабочее время врача (work_slots)

## GET `/api/doctors/{doctor_id}/work-slots?date=2026-03-10`

```json
[
  {
    "work_slot_id": 5,
    "date": "2026-03-10",
    "start_time": "09:00",
    "end_time": "13:00",
    "slot_minutes": 30,
    "break_start": "11:00",
    "break_end": "11:30"
  }
]
```

---

## POST `/api/work-slots`

> админ / регистратура

```json
{
  "doctor_id": 1,
  "date": "2026-03-10",
  "start_time": "09:00",
  "end_time": "13:00",
  "slot_minutes": 30,
  "break_start": "11:00",
  "break_end": "11:30"
}
```

---

# Слоты (генерируемые, read-only для фронта)

## GET `/api/doctors/{doctor_id}/slots?date=2026-03-10`

> **ВАЖНО:** фронт не генерирует слоты — только отображает

```json
[
  {
    "slot_datetime": "2026-03-10T09:00",
    "slot_end_datetime": "2026-03-10T09:30",
    "is_break": false,
    "is_available": true
  },
  {
    "slot_datetime": "2026-03-10T11:00",
    "slot_end_datetime": "2026-03-10T11:30",
    "is_break": true,
    "is_available": false
  }
]
```

---

# Записи на приём (appointments)

## GET `/api/appointments?doctor_id=1&date=2026-03-10`

```json
[
  {
    "appt_id": 100,
    "doctor_id": 1,
    "patient_id": 10,
    "slot_datetime": "2026-03-10T09:00",
    "status": "booked"
  }
]
```

---

## POST `/api/appointments`

> запись на свободный слот

```json
{
  "doctor_id": 1,
  "patient_id": 10,
  "slot_datetime": "2026-03-10T09:30"
}
```

### Ответ

```json
{
  "appt_id": 101,
  "status": "booked"
}
```

---

## PATCH `/api/appointments/{appt_id}`

> отмена или завершение

```json
{
  "status": "canceled"
}
```

или

```json
{
  "status": "completed"
}
```

---

# Отчёт «Загрузка расписания»

## GET `/api/reports/utilization?doctor_id=1&from=2026-03-01&to=2026-03-07`

```json
[
  {
    "doctor_id": 1,
    "date": "2026-03-10",
    "slots_total": 8,
    "slots_booked": 6,
    "utilization": 0.75
  }
]
```

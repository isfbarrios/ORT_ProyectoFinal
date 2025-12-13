// src/mocks/tableAvailabilityMock.js

const tableAvailabilityMock = [
    {
        "id": 1,
        "name": "Mesa 1",
        "description": "Cerca de la ventana",
        "chairsAmount": 4,
        "shifts": [
            {
                "id": { "tableId": 1, "shiftId": 1 }, "openTime": "19", "closeTime": "20",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 1, "shiftId": 2 }, "openTime": "20", "closeTime": "21",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 1, "shiftId": 3 }, "openTime": "21", "closeTime": "22",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 1, "shiftId": 4 }, "openTime": "22", "closeTime": "23",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 1, "shiftId": 5 }, "openTime": "23", "closeTime": "24",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            }
        ]
    },
    {
        "id": 2,
        "name": "Mesa 2",
        "description": "Patio interno",
        "chairsAmount": 2,
        "shifts": [
            {
                "id": { "tableId": 2, "shiftId": 1 }, "openTime": "19", "closeTime": "20",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 2, "shiftId": 2 }, "openTime": "20", "closeTime": "21",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 2, "shiftId": 3 }, "openTime": "21", "closeTime": "22",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 2, "shiftId": 4 }, "openTime": "22", "closeTime": "23",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 2, "shiftId": 5 }, "openTime": "23", "closeTime": "24",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            }
        ]
    },
    {
        "id": 3,
        "name": "Mesa 3",
        "description": "Sector principal",
        "chairsAmount": 6,
        "shifts": [
            {
                "id": { "tableId": 3, "shiftId": 1 }, "openTime": "19", "closeTime": "20",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 3, "shiftId": 2 }, "openTime": "20", "closeTime": "21",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 3, "shiftId": 3 }, "openTime": "21", "closeTime": "22",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 3, "shiftId": 4 }, "openTime": "22", "closeTime": "23",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            },
            {
                "id": { "tableId": 3, "shiftId": 5 }, "openTime": "23", "closeTime": "24",
                "state": { "id": 1, "name": "Libre", "description": "Mesa disponible" }
            }
        ]
    }
];

export default tableAvailabilityMock;

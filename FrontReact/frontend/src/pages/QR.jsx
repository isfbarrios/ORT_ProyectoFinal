import React from "react";
import QRCode from "react-qr-code";

const QRCodeGenerator = ({ value }) => {
  return (
    <div style={{ padding: "1rem", background: "white", width: "fit-content" }}>
      <QRCode value={'https://localhost:8080?tableId=1' + value} />
    </div>
  );
};

export default QRCodeGenerator;

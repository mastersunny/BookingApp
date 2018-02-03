package mastersunny.unitedclub.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.barcode.BarcodeCaptureActivity;

public class BarcodeReaderActivity extends AppCompatActivity {

    private TextView scan_format, scan_content;
    public static final int BARCODE_READER_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        initLayout();
    }

    private void initLayout() {
        scan_format = findViewById(R.id.scan_format);
        scan_content = findViewById(R.id.scan_content);
        findViewById(R.id.startReading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BarcodeReaderActivity.this, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barCode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    scan_content.setText(barCode.displayValue);
                } else
                    scan_content.setText(R.string.no_barcode_captured);
            }
        }

    }
}

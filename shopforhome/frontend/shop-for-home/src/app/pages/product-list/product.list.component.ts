import { Component, Injectable, OnDestroy, OnInit } from "@angular/core";
import { UserService } from "../../services/user.service";
import { ProductService } from "../../services/product.service";
import { JwtResponse } from "../../response/JwtResponse";
import { Subscription } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { CategoryType } from "../../enum/CategoryType";
import { ProductStatus } from "../../enum/ProductStatus";
import { Role } from "../../enum/Role";
import { ExcelService } from "../../services/ExcelService";
import { Router } from "@angular/router";
@Injectable({
  providedIn: "root",
})
@Component({
  selector: "app-product.list",
  templateUrl: "./product.list.component.html",
  styleUrls: ["./product.list.component.css"],
})
export class ProductListComponent implements OnInit, OnDestroy {
  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private excelService: ExcelService,
    private router: Router
  ) {}

  Role = Role;
  currentUser: JwtResponse;
  page: any;
  CategoryType = CategoryType;
  ProductStatus = ProductStatus;
  private querySub: Subscription;
  response: any;
  productInfo = [];

  data: any[];
  shortLink: string = "";
  loading: boolean = false; 
  file: File = null; 

  ngOnInit() {
    this.querySub = this.route.queryParams.subscribe(() => {
      this.update();
    });
  }

  ngOnDestroy(): void {}

  update() {
    if (this.route.snapshot.queryParamMap.get("page")) {
      const currentPage = +this.route.snapshot.queryParamMap.get("page");
      const size = +this.route.snapshot.queryParamMap.get("size");
      this.getProds(currentPage, size);
    } else {
      this.getProds();
    }
  }

  getProds(page: number = 1, size: number = 10) {
    this.productService.getAllInPage(+page, +size).subscribe((page) => {
      this.page = page;
    });
  }

  remove(productId) {
    this.productService.delelte(productId).subscribe((_) => {
      this.update();
    });
  }

  exportAsXLSX(): void {
    this.productService
      .getAll()
      .subscribe((response) => (this.productInfo = response));
    console.log(this.data);
    this.excelService.exportAsExcelFile(this.productInfo, "Report");
  }

  afuConfig = {
    uploadAPI: {
      url: "http://localhost:8081/api/csv/upload",
    },
  };

  onChange(event) {
    this.file = event.target.files[0];
  }

  
  onUpload() {
    this.loading = !this.loading;
    console.log(this.file);
    this.productService.upload(this.file).subscribe((event: any) => {
      if (typeof event === "object") {
        
        this.shortLink = event.link;

        this.loading = false; 
      }
    });
  }

  onSubmit() {
    this.router.navigate(["/email"]);
  }
}

using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace EntreeAPI.Migrations
{
    public partial class userchange : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Guests_Users_UserId",
                table: "Guests");

            migrationBuilder.DropIndex(
                name: "IX_Guests_UserId",
                table: "Guests");

            migrationBuilder.RenameColumn(
                name: "UserId",
                table: "Guests",
                newName: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_Guests_userId",
                table: "Guests",
                column: "userId",
                unique: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Guests_Users_userId",
                table: "Guests",
                column: "userId",
                principalTable: "Users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Guests_Users_userId",
                table: "Guests");

            migrationBuilder.DropIndex(
                name: "IX_Guests_userId",
                table: "Guests");

            migrationBuilder.RenameColumn(
                name: "userId",
                table: "Guests",
                newName: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_Guests_UserId",
                table: "Guests",
                column: "UserId");

            migrationBuilder.AddForeignKey(
                name: "FK_Guests_Users_UserId",
                table: "Guests",
                column: "UserId",
                principalTable: "Users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
